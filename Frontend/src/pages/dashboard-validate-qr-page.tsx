import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { useEffect, useState } from "react";
import { Scanner } from "@yudiel/react-qr-scanner";
import {
  TicketValidationMethod,
  TicketValidationStatus,
} from "@/domain/domain";
import { AlertCircle, Check, X } from "lucide-react";
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";
import { validateTicket } from "@/lib/api";
import { useAuth } from "react-oidc-context";
import { Link } from "react-router";
import { useNavigate } from "react-router";


const DashboardValidateQrPage: React.FC = () => {
  const { isLoading, user } = useAuth();
  const [isManual, setIsManual] = useState(false);
  const [data, setData] = useState<string | undefined>();
  const [error, setError] = useState<string | undefined>();
  const [validationStatus, setValidationStatus] = useState<
    TicketValidationStatus | undefined
  >();
  const navigate = useNavigate();

  const handleReset = () => {
    setIsManual(false);
    setData(undefined);
    setError(undefined);
    setValidationStatus(undefined);
  };

  const navigateBack =()=>{
    navigate("/")
  }

  const resetFun = async ()=>{

       if(validationStatus === TicketValidationStatus.INVALID){

      await new Promise(r=> setTimeout( r, 3000));

      handleReset();
   }
  }
  useEffect(  ()=>{
      resetFun();
  },[validationStatus])

  const handleError = (err: unknown) => {
    if (err instanceof Error) {
      setError(err.message);
    } else if (typeof err === "string") {
      setError(err);
    } else {
      setError("An unknown error occurred");
    }
  };

  const handleValidate = async (id: string, method: TicketValidationMethod) => {
    if (!user?.access_token) {
      return;
    }
    try {
      const response = await validateTicket(user.access_token, {
        id,
        method,
      });
      setValidationStatus(response.status);
    } catch (err) {
      handleError(err);
    }
  };

  if (isLoading || !user?.access_token) {
    <p>Loading...</p>;
  }

  return (< div className="bg-[#000066]">
    <Link to={`/`} ><Button className="ml-6 mt-1 text-xl cursor-pointer ">Back</Button></Link>
    <div className="min-h-screen bg-[#000066] text-white flex justify-center items-center">
      <div
        className="border border-[#b2bfe6] max-w-sm w-full p-4 bg-[#19264d] grid gap-0 " >
        {error && (
          <div className=" text-black w-full">
            <Alert variant="destructive" className="bg-red-200 border-red-700 w-80">
              <AlertCircle className="h-1 w-4" />
              <AlertTitle>Error</AlertTitle>
              <AlertDescription>{error}</AlertDescription>
            </Alert>
          </div>
        )}
        {/* Scanner Viewport */}
        <div className="rounded-lg overflow-hidden mx-auto mb-4 relative ">
          <Scanner
            key={`scanner-${data}-${validationStatus}`}
            onScan={(result) => {
              if (result) {
                const qrCodeId = result[0].rawValue;
                setData(qrCodeId);
                handleValidate(qrCodeId, TicketValidationMethod.QR_SCAN);
              }
            }}
            onError={handleError}
          />

          {validationStatus && (
            <div className="absolute inset-0 flex items-center justify-center">
              {validationStatus === TicketValidationStatus.VALID ? (
                <div className="bg-green-500 rounded-full p-4">
                  <Check className="w-20 h-20" />
                </div>
              ) : (
                <div className="bg-red-500 rounded-full p-4">
                  <X className="w-20 h-20" />
                </div>
              )}
            </div>
          )}
        </div>

        {isManual ? (
          <div className="pb-8">
            <Input
              className="w-full text-white text-lg mb-8"
              onChange={(e) => setData(e.target.value)}
            />
            <Button
              className="bg-[#33cc33] w-full h-[50px] hover:bg-[#5dd55d] text-xl cursor-pointer mt-0"
              onClick={() =>
                handleValidate(data || "", TicketValidationMethod.MANUAL)
              }
            >
              Submit
            </Button>
          </div>
        ) : (
          <div>
            <div className="border-white border-2 h-12 rounded-md font-mono flex justify-center items-center">
              <span>{data || "Scan for Result"}</span>
            </div>
            <Button
              className="bg-[#ff8000] hover:bg-[#ff8c1a] border-gray-500 border-2 w-full h-[70px] text-xl my-8 cursor-pointer mt-4 mb-3"
              onClick={() => setIsManual(true)}
            >
              Manual
            </Button>
          </div>
        )}

        <Button
          className="bg-[#b3b300] hover:bg-[#808000] w-full h-[50px] text-xl my-8 cursor-pointer mt-0"
          onClick={handleReset}
        >
          Reset
        </Button>
      </div>
    </div>
    </div>
  );
};

export default DashboardValidateQrPage;

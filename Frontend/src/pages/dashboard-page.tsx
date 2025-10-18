import { useRoles } from "@/hooks/use-roles";
import { useEffect } from "react";
import { useNavigate } from "react-router";

const DashboardPage: React.FC = () => {
  const { isLoading, isOrganizer, isStaff } = useRoles();
  const navigate = useNavigate();
  // console.log("loading: " +isLoading ,isOrganizer , isStaff);
  useEffect(()=>{

    if (isLoading) {
    <p>Loading...</p>;
    // console.log("loading");
      return;
  }

  if (isOrganizer) {
    // console.log("navigating to events..")
    navigate("/dashboard/events");
    return;
    
  }

  if (isStaff) {

    navigate("/dashboard/validate-qr");
    return;
    
  }
  //  console.log("navigating to tickets..")
  navigate("/dashboard/tickets");

  

  } ,[isLoading, isOrganizer, isStaff])

  return <p>Loading...</p>;
  
};

export default DashboardPage;

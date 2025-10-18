import { ReactNode, useEffect } from "react";
import { useAuth } from "react-oidc-context";
import { Navigate, useLocation } from "react-router";

interface ProtectedRouteProperties {
  children: ReactNode;
}

const ProtectedRoute: React.FC<ProtectedRouteProperties> = ({ children }) => {
  const { isLoading, isAuthenticated  , signinRedirect} = useAuth();
  const location = useLocation();

  useEffect(()=>{
    if(!isLoading && !isAuthenticated){
      localStorage.setItem("redirectPath" , location.pathname+location.search);
      
      signinRedirect();
    }
  } , [ isLoading, isAuthenticated  ,location, signinRedirect]);

  if (isLoading || (!isAuthenticated && typeof window != "undefined") ) {
    return <p>Loading...</p>;
  }

  // if (!isAuthenticated) {
  //   localStorage.setItem(
  //     "redirectPath",
  //     globalThis.location.pathname + globalThis.location.search,
  //   );
  //   return <Navigate to="/login" state={{ from: location }} replace />;
  // }

  return <>{children}</>;
};

export default ProtectedRoute;

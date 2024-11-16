import React, { useEffect, useState } from "react";
import { Navigate } from "react-router-dom";

const ErrorPage = () => {
  const [redirect, setRedirect] = useState(false);

  useEffect(() => {
    const timer = setTimeout(() => {
      setRedirect(true); // Trigger redirection
    }, 3000);

    return () => clearTimeout(timer); // Cleanup the timer on component unmount
  }, []);

  if (redirect) {
    return <Navigate to="/login" replace />;
  }

  return (
    <div>
      <h1>404 - Page Not Found</h1>
      <p>You will be redirected to the login page shortly...</p>
    </div>
  );
};

export default ErrorPage;

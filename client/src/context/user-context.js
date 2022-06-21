import React, { useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useLocalStorage } from "../custom-hooks/use-storage";

const UserContext = React.createContext();

export function useUserContext() {
  return useContext(UserContext);
}

export function UserProvider({ children }) {
  const [token, setToken, remove] = useLocalStorage("AUTH_TOKEN");
  const [userRole, setUserRole, removeUserRole] = useLocalStorage("USER");

  const navigate = useNavigate();

  useEffect(() => {
    const getUserByToken = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/users/me", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        if (response.status === 401) {
          removeUserRole();
          remove();
          navigate('/login');
        }
        const data = await response.json();
        setUserRole(data.role);
      } catch (err) {
        console.log(err);
      }
    };

    if (token && !userRole) {
      getUserByToken();
    }
  }, [token, navigate, removeUserRole, userRole, setUserRole, remove]);
  return (
    <UserContext.Provider value={{ token, setToken, remove, userRole, removeUserRole}}>
      {children}
    </UserContext.Provider>
  );
}

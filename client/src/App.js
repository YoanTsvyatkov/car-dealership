import React from "react";
import { BrowserRouter } from "react-router-dom";
import { UserProvider } from "./context/user-context";
import Navigation from "./components/navigation/Navigation";

export default function App() {
  return (
      <BrowserRouter>
        <UserProvider>
          <Navigation />
        </UserProvider>
      </BrowserRouter>
  );
}

import React from "react";
import { Route, Routes, Navigate } from "react-router-dom";
import Layout from "../layout/layout";
import CarsPage from "../../pages/cars/cars";
import CarModify from "../../pages/car-modify/car-modify";
import CarDetails from "../../pages/car-detail/car-detail";
import Login from "../../pages/login/login";
import Register from "../../pages/register/register";
import { useUserContext } from "../../context/user-context";

export default function Navigation() {
  const { userRole, token } = useUserContext();
  const isUserAuthorized =
    userRole !== undefined && (userRole === "ADMIN" || userRole === "DEALER");
  const isUserAuthenticated = token !== undefined;

  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route index element={<CarsPage />} />
        <Route path="cars" element={<CarsPage />} />
        <Route path="cars/:carId" element={<CarDetails />} />
        <Route
          path="car-modify"
          element={
            isUserAuthorized ? <CarModify /> : <Navigate replace to="/cars" />
          }
        />
        <Route
          path="login"
          element={isUserAuthenticated ? <Navigate replace to="/cars" /> : <Login />}
        />
        <Route path="register"
          element={isUserAuthenticated ? <Navigate replace to="/cars" /> : <Register />} />
      </Route>
    </Routes>
  );
}

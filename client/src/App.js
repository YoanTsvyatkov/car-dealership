import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Layout from "./components/layout/layout";
import CarModify from "./pages/car-modify/car-modify";
import Login from "./pages/login/login";
import Register from "./pages/register/register";
import Report from "./pages/report/report";
import CarDetails from "./pages/car-detail/car-detail";
import { UserProvider } from "./context/user-context";
import CarsPage from "./pages/cars/cars";

export default function App() {
  return (
    <UserProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Layout />}>
            <Route index element={<CarsPage />} />
            <Route path="cars" element={<CarsPage />} />
            <Route path="cars/:carId" element={<CarDetails />} />
            <Route path="car-modify" element={<CarModify />} />
            <Route path="report" element={<Report />} />
            <Route path="login" element={<Login />} />
            <Route path="register" element={<Register />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </UserProvider>
  );
}

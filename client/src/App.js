import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Layout from "./components/layout/layout";
import CarModify from "./pages/car-modify/car-modify";
import Login from "./pages/login/login";
import Register from "./pages/register/register";
import Cars from "./pages/cars/cars";
import Report from "./pages/report/report";
import CarDetails from "./pages/car-detail/car-detail";
import { UserProvider } from "./context/user-context";

export default function App() {
  return (
    <UserProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Layout />}>
            <Route index element={<Cars />} />
            <Route path="cars" element={<Cars />} />
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

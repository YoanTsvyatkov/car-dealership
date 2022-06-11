import  React from "react";
import { Outlet } from "react-router-dom";
import Header from "../header/header";

export default function Layout() {
    return (
      <>
        <Header />
        <div className="content">
          <Outlet />
        </div>
      </>
    );
}
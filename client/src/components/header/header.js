import React from "react";
import { Nav, Navbar } from "react-bootstrap";
import { LinkContainer } from "react-router-bootstrap";
import { useUserContext } from "../../context/user-context";
import styles from "./header.module.css";

export default function Header() {
  const { userRole, token, remove, removeUserRole } = useUserContext();
  const isUserAuthorized =
    userRole != null && (userRole === "ADMIN" || userRole === "DEALER");
  const isUserNotLoggedIn = token === undefined;

  const onUserLogOut = () => {
    remove();
    removeUserRole();
  }

  return (
    <header style={styles.header}>
      <Navbar bg="dark" variant="dark" expand="lg">
        <div className={styles.container}>
          <Navbar.Brand className={styles.navBrand}>
            Car dealership
          </Navbar.Brand>
          <Nav>
            <LinkContainer to="/cars">
              <Nav.Link className={styles.navLink}>Cars</Nav.Link>
            </LinkContainer>
            {isUserAuthorized && (
              <LinkContainer to="/car-modify">
                <Nav.Link className={styles.navLink}>Modify cars</Nav.Link>
              </LinkContainer>
            )}
            {isUserAuthorized && (
              <LinkContainer to="/report">
                <Nav.Link className={styles.navLink}>Report</Nav.Link>
              </LinkContainer>
            )}
            {isUserNotLoggedIn ? (
              <>
                <LinkContainer to="/register">
                  <Nav.Link className={styles.navLink}>Register</Nav.Link>
                </LinkContainer>
                <LinkContainer to="/login">
                  <Nav.Link className={styles.navLink}>Login</Nav.Link>
                </LinkContainer>
              </>
            ) : (
              <Nav.Link className={styles.navLink} onClick={onUserLogOut}>Logout</Nav.Link>
            )}
          </Nav>
        </div>
      </Navbar>
    </header>
  );
}

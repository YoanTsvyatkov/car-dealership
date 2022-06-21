import React, { useEffect, useState } from "react";
import { Form, Button, Alert } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { useUserContext } from "../../context/user-context";
import useForm from "../../custom-hooks/use-form";
import validate from "../../utils/login-form-validation";
import styles from "./login.module.css";

export default function Login() {
  const [alert, setAlert] = useState(false);

  const { setToken } = useUserContext();
  const navigate = useNavigate();

  useEffect(() => {
    let timeout;
    if (alert) {
      timeout = setTimeout(() => {
        setAlert(false);
      }, 5000);
    }

    return () => {
      clearTimeout(timeout);
    }
  }, [alert, setAlert])

  const onFormSubmit = async () => {
    const { username, password } = values;
    const body = {
      username,
      password,
    };

    const response = await fetch("http://localhost:8080/api/login", {
      method: "POST",
      body: JSON.stringify(body),
      headers: {
        "Content-Type": "application/json",
      },
    });

    if (response.status === 401) {
      setAlert(true);
      return;
    }

    const { token } = await response.json();
    setToken(token);
    navigate("/cars");
  };

  const { values, errors, handleSubmit, handleChange } = useForm(
    onFormSubmit,
    validate
  );

  return (
    <>
      <Form onSubmit={handleSubmit} className={styles.container}>
        <Form.Group className={styles.formControl} controlId="formGroupEmail">
          <Form.Label>Username</Form.Label>
          <Form.Control
            type="text"
            isInvalid={!!errors.username}
            onChange={handleChange}
            name="username"
            value={values.username || undefined}
          />
          <Form.Control.Feedback type="invalid">
            {errors.username}
          </Form.Control.Feedback>
        </Form.Group>
        <Form.Group
          className={styles.formControl}
          controlId="formGroupPassword"
        >
          <Form.Label>Password</Form.Label>
          <Form.Control
            type="password"
            isInvalid={!!errors.password}
            onChange={handleChange}
            name="password"
            value={values.password || undefined}
          />
          <Form.Control.Feedback type="invalid">
            {errors.password}
          </Form.Control.Feedback>
        </Form.Group>
        <div className={styles.buttonContainer}>
          <Button variant="primary" type="submit" className={styles.button}>
            Login
          </Button>
        </div>
        {alert && <Alert className={styles.alert} variant="danger">Invalid username or password</Alert>}
      </Form>
    </>
  );
}

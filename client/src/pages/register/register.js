import React, { useEffect, useState } from "react";
import { Form, Button, Alert } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import useForm from "../../custom-hooks/use-form";
import validate from "../../utils/register-form-validation";
import styles from "./register.module.css";

export default function Registration() {
  const navigate = useNavigate();
  const [alert, setAlert] = useState(false);

  useEffect(() => {
    let timeout;
    if (alert) {
      timeout = setTimeout(() => {
        setAlert(false);
      }, 5000);
    }

    return () => {
      clearTimeout(timeout);
    };
  }, [alert, setAlert]);

  const onRegFormSubmit = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/register`, {
        method: "POST",
        body: JSON.stringify(values),
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (response.status !== 200) {
        console.log(values);
        setAlert(true);
        return;
      }
    } catch (err) {
      console.log(err);
    }
    navigate("/login");
  };

  const { values, errors, handleSubmit, handleChange } = useForm(
    onRegFormSubmit,
    validate
  );

  return (
    <>
      <Form onSubmit={handleSubmit} className={styles.container}>
        <h3>Register</h3>
        <Form.Group className={styles.formControl} controlId="formGroupName">
          <Form.Label>Name</Form.Label>
          <Form.Control
            type="text"
            isInvalid={!!errors.name}
            onChange={handleChange}
            name="name"
            value={values.name || ""}
          />
          <Form.Control.Feedback type="invalid">
            {errors.name}
          </Form.Control.Feedback>
        </Form.Group>
        <Form.Group
          className={styles.formControl}
          controlId="formGroupUsername"
        >
          <Form.Label>Username</Form.Label>
          <Form.Control
            type="text"
            isInvalid={!!errors.username}
            onChange={handleChange}
            name="username"
            value={values.username || ""}
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
            value={values.password || ""}
          />
          <Form.Control.Feedback type="invalid">
            {errors.password}
          </Form.Control.Feedback>
        </Form.Group>
        <Form.Group className={styles.formControl} controlId="formGroupEmail">
          <Form.Label>Email</Form.Label>
          <Form.Control
            type="email"
            name="email"
            isInvalid={!!errors.email}
            onChange={handleChange}
            value={values.email || ""}
          />
          <Form.Control.Feedback type="invalid">
            {errors.email}
          </Form.Control.Feedback>
        </Form.Group>
        <Form.Group
          className={styles.formControl}
          controlId="formGroupBirthdate"
        >
          <Form.Label>Date of Birth</Form.Label>
          <Form.Control
            type="date"
            isInvalid={!!errors.birthday}
            name="birthday"
            onChange={handleChange}
            value={values.birthday || undefined}
          />
          <Form.Control.Feedback type="invalid">
            {errors.birthday}
          </Form.Control.Feedback>
        </Form.Group>
        <Form.Group
          className={styles.formControl}
          controlId="formGroupPhoneNumber"
        >
          <Form.Label>Phone Number</Form.Label>
          <Form.Control
            type="text"
            isInvalid={!!errors.phoneNumber}
            name="phoneNumber"
            onChange={handleChange}
            value={values.phoneNumber || ""}
          />
          <Form.Control.Feedback type="invalid">
            {errors.phoneNumber}
          </Form.Control.Feedback>
        </Form.Group>
        <div className={styles.buttonContainer}>
          <Button variant="primary" type="submit" className={styles.button}>
            Register
          </Button>
        </div>
        {alert && (
          <Alert className={styles.alert} variant="danger">
            Invalid registration data
          </Alert>
        )}
      </Form>
    </>
  );
}

import React, { useState } from "react";
import { Modal, Form, Button, Row, Col } from "react-bootstrap";
import { useUserContext } from "../../context/user-context";
import useForm from "../../custom-hooks/use-form";
import validate from "../../utils/car-form-validation";

export default function CarModal({ title, onSubmit, formMethod = 'POST', car = {}}) {
  const [file, setFile] = useState(undefined);
  const {token, remove, removeUserRole } = useUserContext();
  const [fileInputValid, setFileInputValid] = useState(true)
  
  const onFormSubmit = async () => {
    const formData = new FormData()
    formData.append('year', values.year);
    formData.append('name', values.name);
    formData.append('price', values.price);
    formData.append('fuelType', values.fuelType);
    formData.append('transmission', values.transmission);
    formData.append('millage', values.millage);
    formData.append('exteriorColor', values.exteriorColor);
    formData.append('interiorColor', values.interiorColor);
    formData.append('mpg', values.mpg);
    formData.append('carDescription', values.carDescription);
    formData.append('file', file);

    try {
      const formUrl = (formMethod === 'PUT' ?  `http://localhost:8080/api/cars/${car.id}` : 'http://localhost:8080/api/cars');
      const response = await fetch(formUrl, {
        method: formMethod,
        headers: {
          Authorization: `Bearer ${token}` 
        },
        body: formData
      });
      if (response.status === 401) {
        remove()
        removeUserRole()
      } 
      const data = await response.json();
      onSubmit(data)
    } catch(err) {
      console.log(err)
    }
  }

  const {  
    values,
    errors,
    handleChange,
    handleSubmit,
    setErrors
  } = useForm(onFormSubmit, validate, car);

  const formSubmitCallback = (e) => {
    e.preventDefault()

    const isFileInputCorrect = formMethod === 'PUT' || file !== undefined
    setFileInputValid(isFileInputCorrect);

    if (!isFileInputCorrect) {
      setErrors(validate(values))
      return
    }

    handleSubmit(e);
  }

  return (
    <>
      <Modal.Header closeButton>{title}</Modal.Header>
      <Modal.Body>
        <Form onSubmit={formSubmitCallback}>
          <Row>
            <Col>
              <Form.Group className="mb-3" controlId="formGroupEmail">
                <Form.Label>Year</Form.Label>
                <Form.Control type="number" isInvalid={!!errors.year} onChange={handleChange} name="year" value={values.year || undefined} />
                <Form.Control.Feedback type="invalid">
                  {errors.year}
                </Form.Control.Feedback>
              </Form.Group>
            </Col>
            <Col>
              <Form.Group className="mb-3" controlId="formGroupPassword">
                <Form.Label>Name</Form.Label>
                <Form.Control type="text" isInvalid={!!errors.name} onChange={handleChange} name="name" value={values.name || ''}/>
                <Form.Control.Feedback type="invalid">
                  {errors.name}
                </Form.Control.Feedback>
              </Form.Group>
            </Col>
          </Row>
          <Row>
            <Col>
              <Form.Group className="mb-3" controlId="formGroupPassword">
                <Form.Label>Price</Form.Label>
                <Form.Control type="number" isInvalid={!!errors.price} onChange={handleChange} name="price" value={values.price || undefined} />
                <Form.Control.Feedback type="invalid">
                  {errors.price}
                </Form.Control.Feedback>
             </Form.Group>
            </Col>
            <Col>
              <Form.Group className="mb-3" controlId="formGroupPassword">
                <Form.Label>Fuel type</Form.Label>
                <Form.Control type="text" isInvalid={!!errors.fuelType} name="fuelType" onChange={handleChange} value={values.fuelType || ''}/>
                <Form.Control.Feedback type="invalid">
                  {errors.fuelType}
                </Form.Control.Feedback>
              </Form.Group>
            </Col>
          </Row>
          <Row>
            <Col>
              <Form.Group className="mb-3" controlId="formGroupPassword">
                <Form.Label>Transmission</Form.Label>
                <Form.Control type="text" isInvalid={!!errors.transmission} name="transmission" onChange={handleChange} value={values.transmission || ''}/>
                <Form.Control.Feedback type="invalid">
                  {errors.transmission}
                </Form.Control.Feedback>
              </Form.Group>
            </Col>
            <Col>
              <Form.Group className="mb-3" controlId="formGroupPassword">
                <Form.Label>Millage</Form.Label>
                <Form.Control type="number" isInvalid={!!errors.millage} name="millage" onChange={handleChange} value={values.millage || undefined} />
                <Form.Control.Feedback type="invalid">
                  {errors.millage}
                </Form.Control.Feedback>
              </Form.Group>
            </Col>
          </Row>
          <Row>
            <Col>
              <Form.Group className="mb-3" controlId="formGroupPassword">
                <Form.Label>Exterior Color</Form.Label>
                <Form.Control type="text" isInvalid={!!errors.exteriorColor} name="exteriorColor" onChange={handleChange} value={values.exteriorColor || ''} />
                <Form.Control.Feedback type="invalid">
                  {errors.exteriorColor}
                </Form.Control.Feedback>
              </Form.Group>
            </Col>
            <Col>
              <Form.Group className="mb-3" controlId="formGroupPassword">
                <Form.Label>Interior Color</Form.Label>
                <Form.Control type="text" isInvalid={!!errors.interiorColor}  name="interiorColor" onChange={handleChange} value={values.interiorColor || ''} />
                <Form.Control.Feedback type="invalid">
                  {errors.interiorColor}
                </Form.Control.Feedback>
              </Form.Group>
            </Col>
          </Row>
          <Row>
            <Col>
              <Form.Group className="mb-3" controlId="formGroupPassword">
                <Form.Label>Mpg</Form.Label>
                <Form.Control type="number" isInvalid={!!errors.mpg} name="mpg" onChange={handleChange} value={values.mpg || undefined} />
                <Form.Control.Feedback type="invalid">
                  {errors.mpg}
                </Form.Control.Feedback>
              </Form.Group>
            </Col>
            <Col>
              <Form.Group controlId="formFile" className="mb-3">
                <Form.Label>Car photo</Form.Label>
                <Form.Control type="file" isInvalid={!fileInputValid} onChange={(e) => setFile(e.target.files[0])}/>
                <Form.Control.Feedback type="invalid">
                  File is required
                </Form.Control.Feedback>
              </Form.Group>
            </Col>
          </Row>
          <Row>
            <Col>
              <Form.Group className="mb-3" controlId="formGroupPassword">
                <Form.Label>Car description</Form.Label>
                <Form.Control as="textarea" isInvalid={!!errors.carDescription} rows={3}  name="carDescription"  onChange={handleChange} value={values.carDescription || ''} />
                <Form.Control.Feedback type="invalid">
                  {errors.carDescription}
                </Form.Control.Feedback>
              </Form.Group>
            </Col>
          </Row>
          <Button variant="primary" type="submit">
            Submit
          </Button>
        </Form>
      </Modal.Body>
    </>
  );
}

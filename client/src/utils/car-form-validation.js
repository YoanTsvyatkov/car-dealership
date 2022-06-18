export default function validate(values) {
    let errors = {};
    if (!values.name) {
      errors.name = 'Name is required';
    }
    if (!values.year) {
        errors.year = 'Year is required';
    }
    if (values.year < 0) {
        errors.year = 'Year cannnot be negative';
    }
    if (!values.fuelType) {
        errors.fuelType = 'Fuel type is required';
    }
    if (!values.transmission) {
        errors.transmission = 'Transmission is required';
    }
    if (!values.millage) {
        errors.millage = 'Millage is required';
    }
    if (values.millage < 0) {
        errors.millage = 'Millage cannot be negative';
    }
    if (!values.price) {
        errors.price = 'Price is required';
    }
    if (values.price <= 0) {
        errors.price = 'Price cannot be zero or negative';
    }
    if (!values.exteriorColor) {
        errors.exteriorColor = 'Exterior color is required';
    }
    if (!values.interiorColor) {
        errors.interiorColor = 'Interior color is required';
    }
    if (!values.mpg) {
        errors.mpg = 'Mpg is required';
    }
    if (values.mpg < 0) {
        errors.mpg = 'Mpg cannot be negative';
    }
    if (!values.carDescription) {
        errors.carDescription = 'Car description is required';
    }
    return errors;
  };
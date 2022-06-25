export default function validate(values) {
    let errors = {}

    if(!values.name) {
        errors.name = "Name must not be empty"
    }

    if(!values.username) {
        errors.username = "Username must not be empty"
    }

    if(values.username && values.username.length < 6) {
        errors.username = "Username must be at least 6 characters"
    }

    
    if(!values.password) {
        errors.password = "Password must not be empty"
    }

    if(values.password && values.password.length < 6) {
        errors.password = "Password must be at least 6 characters"
    }

    if(!values.birthday) {
        errors.birthday = "Date of birth must not be empty"
    }

    if(!values.email) {
        errors.email = "Email must not be empty"
    }

    if(!values.phoneNumber) {
        errors.phoneNumber = "Phone number must not be empty"
    }

    if(values.phoneNumber && values.phoneNumber.length !== 10) {
        errors.phoneNumber = "Phone number must be 10 digits long"
    }

    return errors;
}
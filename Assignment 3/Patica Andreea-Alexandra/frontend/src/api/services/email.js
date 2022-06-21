import emailjs from '@emailjs/browser';

export function configureEmail(data) {
    return {
        to_email: "alexandrapatica29@gmail.com",
        to_name: data.username,
        property_name: data.property.name,
        from_date: data.date
    };
}


export function sendEmail(configureEmail) {
    console.log(configureEmail);
    emailjs.send('service_1mgkzjr', 'template_ucw1rh5', configureEmail, 'QDoIQT3AzvJGFkfFk')
        .then((result) => {
            console.log('SUCCESS!', result.text);
        }, (error) => {
            console.log('FAILED...', error.text);
        });
}

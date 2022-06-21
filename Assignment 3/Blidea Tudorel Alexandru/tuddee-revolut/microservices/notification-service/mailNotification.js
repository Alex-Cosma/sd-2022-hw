const nodemailer = require('nodemailer');
const fs = require('fs');

const translations = JSON.parse(fs.readFileSync("translations.json"));

let transporter =  undefined;

const configureTransporter = (emailConfig) => {
      transporter = nodemailer.createTransport(emailConfig);
}

const sendSimpleMail = (handler, { from, recipients, subject, text }) => {
      if (transporter === undefined) {
        console.log("Transporter undefined");
        return;
      }
      transporter.sendMail({
            from: from,
            to: recipients,
            subject: subject,
            text: text
      }, handler);
};

/*
      interface currency {
            name: String,
            value: Number,
      }
      currencies: currency[]
*/
const sendCurrencyUpdate = (handler, from, recipients, currencies, language = "romanian") => {
      if (transporter === undefined) {
        console.log("Transporter undefined");
        return;
      }
      const html = /*html*/ `
            <div style="margin: 2rem; padding: 1rem; display: flex; flex-direction: column; justify-content: center; align-items: center">
                  <div>
                        <img src="https://upload.wikimedia.org/wikipedia/commons/c/c9/Logo_Revolut.png"
                              width="150" height="50"
                        />
                  </div>
                  <div>
                        <h1>${translations[language]["exchanges"]["title"]}</h1>
                  </div>
                  <div style="max-width: 30vw; display: flex; flex-direction: column; justify-content: center; align-items: center; background-color: #F3F4F5; padding: 0.5rem 2rem 0.5rem 2rem">
                        <div style="align-self: flex-start;">
                              <ul style="font-size: 1.5rem; margin: 0.1rem">
                                    ${ currencies.map(currency => currency["name"] + ": " + currency["value"]).join("\n") }
                              </ul>
                        </div>
                        <button style="display: block; background-color: #0666EB; color: white; font-size: 1.3rem; font-weight: 500; border: none; border-radius: 0.8rem; cursor: pointer; margin: 1rem 0rem 0rem 0.5rem; padding: 0.3rem 0.6rem 0.3rem 0.6rem">
                              Exchange now!
                        </button>
                  </div>
            </div>
      `;
      transporter.sendMail({
            from: from,
            to: recipients,
            subject: `${translations[language]["welcome"]["title"]}, ${to}!`,
            html: html
      }, handler);
};

const sendWelcomeMail = (handler, from, to, currentOffers=[], language="romanian") => {
      if (transporter === undefined) {
        console.log("Transporter undefined");
        return;
      }
      const html = /*html*/ `
            <div style="margin: 2rem; padding: 1rem; display: flex; flex-direction: column; justify-content: center; align-items: center">
                  <div>
                        <img src="https://upload.wikimedia.org/wikipedia/commons/c/c9/Logo_Revolut.png"
                              width="150" height="50"
                        />
                  </div>
                  <div>
                        <h1>${translations["language"]["welcome"]["title"]}, ${to}!</h1>
                  </div>
                  <div>
                        <h3>${translations["language"]["welcome"]["text"]}</h3>
                  </div>
                  <div style="max-width: 30vw; display: flex; flex-direction: column; justify-content: center; align-items: center; background-color: #F3F4F5; padding: 0.5rem 2rem 0.5rem 2rem">
                        <div>
                              <h2 style="margin: 0.2rem"> ${translations["language"]["welcome"]["offers"]} </h2>
                              <ul style="font-size: 1.5rem; margin: 0.1rem">
                                    ${currentOffers.map(offer => "<li>" + offer + "</li>").join("\n")}
                              </ul>
                        </div>
                        <button style="display: block; background-color: #0666EB; color: white; font-size: 1.3rem; font-weight: 500; border: none; border-radius: 0.8rem; cursor: pointer; margin: 1rem 0rem 0rem 0.5rem; padding: 0.3rem 0.6rem 0.3rem 0.6rem">
                              ${translations["language"]["welcome"]["seeAllOffersButton"]}
                        </button>
                  </div>
                  <div style="margin-top: 0.5rem; margin-bottom: 0.5rem">
                        ${translations["language"]["welcome"]["cards"]}
                  </div>
                  <div style="margin-top: 0.5rem; margin-bottom: 0.5rem; background-image: url('https://cdn.revolut.com/website/assets/revolut-share-preview.jpg'); width: 30vw; height: 30vh; background-size: cover;">
                  </div>
                  <button style="display: block; background-color: #0666EB; color: white; font-size: 1.3rem; font-weight: 500; border: none; border-radius: 0.8rem; cursor: pointer; margin: 1rem 0rem 0rem 0.5rem; padding: 0.3rem 0.6rem 0.3rem 0.6rem">
                              ${translations["language"]["welcome"]["orderCardButton"]}
                  </button>
                  <div style="width: 500px; display: flex; flex-direction: row; justify-content: center; align-items: center; padding: 0.5rem;">
                        <button style="white-space: nowrap; display: block; background-color: #ed44d4; color: white; font-size: 1.3rem; font-weight: 500; border: none; border-radius: 0.8rem; cursor: pointer; margin: 0.2rem 0rem 0rem 0.2rem; padding: 0.3rem 0.6rem 0.3rem 0.6rem">
                              ${translations["language"]["welcome"]["currecySubscribeButton"]}
                        </button>
                        <button style="white-space: nowrap; display: block; background-color: #ed44d4; color: white; font-size: 1.3rem; font-weight: 500; border: none; border-radius: 0.8rem; cursor: pointer; margin: 0.2rem 0rem 0rem 0.2rem; padding: 0.3rem 0.6rem 0.3rem 0.6rem">
                              ${translations["language"]["welcome"]["shareWithOthersButton"]}
                        </button>
                        <button style="white-space: nowrap; display: block; background-color: #ed44d4; color: white; font-size: 1.3rem; font-weight: 500; border: none; border-radius: 0.8rem; cursor: pointer; margin: 0.2rem 0rem 0rem 0.2rem; padding: 0.3rem 0.6rem 0.3rem 0.6rem">
                              ${translations["language"]["welcome"]["newsletterUpdateButton"]}
                        </button>
                  </div>
            </div>
      `;
      transporter.sendMail({
            from: from,
            to: to,
            subject: `${translations["language"]["welcome"]["title"]}, ${to}!`,
            html: html
      }, handler);
};

module.exports = { configureTransporter, sendSimpleMail, sendWelcomeMail, sendCurrencyUpdate };
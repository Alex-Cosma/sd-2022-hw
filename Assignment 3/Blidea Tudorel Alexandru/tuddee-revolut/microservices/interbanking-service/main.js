const express = require('express');
const cors = require('cors');
const fs = require('fs');

const config = JSON.parse(fs.readFileSync("config.json"));

const app = express();
app.use(express.json());
app.use(cors(config.cors));

config.banks.forEach(bank => {
      app.put(`${bank.endpoint}/transfer`, (req, res) => {
            const { identifytoken, bankname } = req.headers;
            const { fromDetails } = req.body;
            if (!identifytoken?.toUpperCase().startsWith(bank?.prefixToken.toUpperCase())) {
                  res.status(401).json({ message: "Wrong token! You are not authorized to communicate for transfers!" });
            }
            else if (bank.name != req.body.bankName) {
                  res.status(400).json({ message: "Wrong bank!" });
            }
            else if (!req.body?.iban.toUpperCase().startsWith(bank.prefixIban)) {
                  res.status(400).json({ message: "Iban not from this bank!" });
            } else {
                  const details = `Got a transfer from ${ bankname } of $${ req.body.amount } from(${fromDetails.firstName} ${fromDetails.lastName} -- [${fromDetails.email}|${fromDetails.iban}]) to ${ req.body.iban }`;
                  console.log(details);
                  res.status(200).json({
                        message: "Transfer Complete!!",
                        details
                  });
            }
      });
});

const generateIban = (prefix) => {
      let result = prefix;
      while (result.length < config.ibanLength) {
            result += Math.floor(Math.random() * 10);
      }
      return result;
};

const getPrefix = (abbreviation) => {
      const foundBank = config.banks.find(bank => bank.abbreviation === abbreviation);
      return foundBank ? foundBank.prefixIban : "NOT_FOUND";
};

app.get("/iban/gen/:abbreviation", (req, res) => {
      const abbreviation = req.params?.abbreviation;
      res.status(200).json({
        iban: generateIban(getPrefix(abbreviation))
      });
});

app.listen(config.port, () => {
      console.log(`Application started on port: ${config.port}`);
});
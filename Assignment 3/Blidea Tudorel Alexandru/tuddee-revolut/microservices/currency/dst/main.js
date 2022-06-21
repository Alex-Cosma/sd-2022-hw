"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
const fs_1 = __importDefault(require("fs"));
const cors_1 = __importDefault(require("cors"));
const http_1 = __importDefault(require("http"));
const socket_io_1 = require("socket.io");
const CurrencyRate_js_1 = __importDefault(require("./CurrencyRate.js"));
const MapSerializer_js_1 = __importDefault(require("./MapSerializer.js"));
const config = JSON.parse(fs_1.default.readFileSync("config.json").toString());
const baseRates = JSON.parse(fs_1.default.readFileSync(config.ratesFile).toString());
const availableCurrencies = Object.keys(JSON.parse(fs_1.default.readFileSync(config.ratesFile).toString()));
const app = (0, express_1.default)();
app.use((0, cors_1.default)(config.corsConfig));
const server = http_1.default.createServer(app);
const io = new socket_io_1.Server(server, {
    cors: {
        origin: ["http://localhost:8080", "http://localhost:3000"]
    }
});
let currency = new CurrencyRate_js_1.default(baseRates, config.baseCurrency, config.fluctuation, config.quotientRate);
setInterval(() => {
    io.emit("latest-currency", (0, MapSerializer_js_1.default)(currency.fluctuate()));
}, config.fluctuatePeriod);
app.get("/rates/latest", (req, res) => {
    res.status(200).send((0, MapSerializer_js_1.default)(currency.getLastCurrencyRate()));
});
app.get("/rates/last/:last", (req, res) => {
    const n = +req.params.last;
    res.status(200).send(currency.getLastN(n).map(currencyRate => (0, MapSerializer_js_1.default)(currencyRate)));
});
app.get("/rates/evolution", (req, res) => {
    res.status(200).send(currency.getLastEvolution().map(currencyRate => (0, MapSerializer_js_1.default)(currencyRate)));
});
app.get("/rates/availableCurrencies", (req, res) => {
    res.status(200).send(availableCurrencies);
});
app.get("/rates/baseCurrency", (req, res) => {
    res.status(200).send(config.baseCurrency);
});
io.on('connection', (socket) => {
    console.log(`a user connected: ${socket.id}`);
});
server.listen(config.port, () => {
    console.log(`Application started on port ${config.port}`);
});

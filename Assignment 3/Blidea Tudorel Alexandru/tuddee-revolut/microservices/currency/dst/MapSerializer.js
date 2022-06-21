"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const mapSerializer = (map) => {
    const entries = [...map];
    return Object.fromEntries(entries);
};
exports.default = mapSerializer;

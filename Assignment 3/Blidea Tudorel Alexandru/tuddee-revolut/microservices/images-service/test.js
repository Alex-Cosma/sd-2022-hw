const fs = require("fs");
const { imageExists, getImagePath, addImage, fileExtensionFolderExists } = require("./imageSearcher.js");

console.log(fileExtensionFolderExists('jpg') == true);
console.log(fileExtensionFolderExists('jpeg') == true);
console.log(fileExtensionFolderExists('png') == true);
console.log(fileExtensionFolderExists('caca') == true);
console.log(fileExtensionFolderExists('pmp') == true);
console.log(fileExtensionFolderExists('bmp') == true);
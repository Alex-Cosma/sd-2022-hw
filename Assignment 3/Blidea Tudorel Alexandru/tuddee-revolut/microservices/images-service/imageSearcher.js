const fs = require('fs');
const crypto = require('crypto');

const imagesFolderName = 'images';
const algorithm = "sha256";

const fileExtensionFolderExists = (extension) => {
      return fs.existsSync(`${__dirname}/${imagesFolderName}/${extension}`);
}

const getImageExtension = (imageFullName) => {
      const splitedName = imageFullName.split('.');
      if (splitedName.length < 2) {
            throw new Error('Not a valid name');
      }
      return splitedName[splitedName.length - 1];
}

const getImageNewName = (imageName, image) => {
      const imageExtension = getImageExtension(imageName);
      return crypto.createHash(algorithm)
            .update(imageName)
            .update(image)
            .digest("hex") + "." + imageExtension;
};

const imageExists = (imageName) => {
      const imageExtension = getImageExtension(imageName);
      return fs.existsSync(`${__dirname}/${imagesFolderName}/${imageExtension}/${imageName}`);
};

const getImagePath = (imageName) => {
      return `${__dirname}/${imagesFolderName}/${getImageExtension(imageName)}/${imageName}`;
};

const addImage = (imageName, image) => {
      const imageExtension = getImageExtension(imageName);
      const imageNewName = getImageNewName(imageName, image);
      if (!fileExtensionFolderExists(imageExtension)) {
            fs.mkdirSync(`${__dirname}/${imagesFolderName}/${imageExtension}`);
      }
      fs.writeFileSync(`${__dirname}/${imagesFolderName}/${imageExtension}/${imageNewName}`, image.split(";base64,").pop(), {encoding: 'base64'}, () => {});
      return imageNewName;
};

module.exports = { imageExists, getImagePath, addImage, fileExtensionFolderExists };
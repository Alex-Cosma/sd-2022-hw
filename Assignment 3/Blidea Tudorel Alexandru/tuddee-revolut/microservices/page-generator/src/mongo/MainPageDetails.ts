const Schema = require("mongoose").Schema;

interface Information {
      title: String,
      informationParagraphs: String[],
      imageLink: String,
};

interface Plan {
      title: String,
      details: String,
      price: Number,
};

interface MainPageDetails {
      heroText: String,
      heroMotto: String,
      infos: Information[],
      plans: Plan[],
}

const blogSchema = new Schema({
      
});

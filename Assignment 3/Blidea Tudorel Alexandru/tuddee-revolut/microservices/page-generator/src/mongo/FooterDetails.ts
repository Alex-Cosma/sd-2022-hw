const Schema: any = require("mongoose").Schema;

interface SiteMap {
  title: String;
  pages: [String, String][];
}

interface SocialMedia {
  link: String;
  mdiIcon: String;
  color: String;
}

interface FooterDetails {
  siteMaps: SiteMap[];
  contactInformation: String[];
  socialMedia: SocialMedia[];
}

const footerSchema = new Schema({
  siteMaps: String,
  contactInformations: String,
  socialMedia: String,
});

export { SiteMap, SocialMedia, FooterDetails, footerSchema };

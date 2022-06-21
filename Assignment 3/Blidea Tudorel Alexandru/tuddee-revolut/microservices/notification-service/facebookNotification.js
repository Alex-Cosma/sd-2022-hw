const FB = require('fb');

const createMessageForAComplexPost = (postElements) => {
      if (!postElements) {
            return "Blank Post";
      }
      const result = postElements.map(elem => {
            const message = elem.message ? elem.message : "";
            const list = !elem.list || elem.list.length === 0 ? [] : elem.list;
            for (let i = 0; i < list.length; i++) {
                  list[i] = ( elem.ordered ? ` ${i + 1}. ` : " - " ) + list[i];
            }
            return message + "\n" + list.join("\n");
      });
      return result.join("\n");
}

const makeASimpleFacebookPost = (token, message) => {
      FB.api("/me/feed", "POST", {
            message: message,
            access_token: token,
      }, (response) => console.log(response));
}

/*
      interface postDetails {
            elements: {
                  message: String, 
                  list: optional String,
                  ordered: Boolean
            }[],
      }
*/
const makeAComplexFacebookPost = (token, post) => {
      FB.api("/me/feed", "POST", {
            message: createMessageForAComplexPost(post.elements),
            access_token: token
      }, (response) => console.log(response));
}

module.exports = { makeASimpleFacebookPost, makeAComplexFacebookPost };

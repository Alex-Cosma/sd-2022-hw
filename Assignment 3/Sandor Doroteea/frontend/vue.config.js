module.exports = {
  transpileDependencies: ["vuetify"],
};

module.exports = {
  devServer: {
    disableHostCheck: true,
    port: 8091,
    public: "0.0.0.0:8091",
  },
  publicPath: "/",
};

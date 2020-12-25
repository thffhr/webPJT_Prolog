export default {
  TITLE: "SSAFY 개발블로그",
  baseUrl:
    "http://ec2-54-180-116-157.ap-northeast-2.compute.amazonaws.com:8888",
  LS_KEY: {
    USER_TOKEN: "",
  },

  IS_LOGED_IN: localStorage["email"] ? true : false,

  ERROR: {
    FRONT_ERROR: "FE00",
  },
  URL_TYPE: {
    MAIN: {
      NOLOGINHOME: "nologinhome",
      LOGINHOME: "loginhome",
    },
    USER: {
      // LOGIN: "login",
      JOIN: "join",
      UPDATE: "update",
      FINDUSERBYEMAIL: "finduserbyemail",
      EMAILCOMPARE: "emailcompare",
    },
    POST: {
      MANAGEPORTFOLIO: "manageportfolio",
      MANAGEEXPERIENCE: "manageexperience",
      PORTFOLIODETAILS: "portfoliodetails",
      MANAGEAPPLY: "manageapply",
      MYCOMPONENT: "mycomponent",
    },
  },
};

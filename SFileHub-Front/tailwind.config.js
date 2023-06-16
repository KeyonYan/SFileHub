// tailwind.config.js
export const purge = [];
export const darkMode = false;
export const theme = {
  extend: {
    colors: {
      //将 colors 类名替换为你的颜色
      primary: "#ff0000",
    },
    zIndex: {
      //在zindex当中添加你的自定义类名z-1
      "-1": "-1",
    },
  },
};
export const variants = {};
export const plugins = [];

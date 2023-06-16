// tailwind.config.js
module.exports = {
  purge: [], //如果你安装的是tailwind v3 以上的版本，这里应该换成content
  darkMode: false, // or 'media' or 'class'
  //这里可以对tailwind的类名进行扩展
  theme: {
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
  },
  variants: {},
  plugins: [],
};

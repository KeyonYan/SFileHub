// router/index.js
import LoginPage from '../pages/LoginPage'
import IndexPage from '../pages/IndexPage'

const routes = [
  {
    path: "/",
    component: IndexPage
  },
  {
    path: "/login",
    component: LoginPage
  }
];

export default routes
import React from 'react';
import { useStore } from '../../main';

const LoginPage: React.FC = () => {
  const userName = useStore(state => state.userName);
  return (
    <div>
        Welcome Home Page, {userName}
    </div>
  );
};
export default LoginPage;
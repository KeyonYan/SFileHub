import { useStore } from '../../main';
import React from 'react';
import type { MenuProps } from 'antd';
import { Dropdown, Switch } from 'antd';
import { useState } from 'react';


const items: MenuProps['items'] = [
    {
      label: (
        <a target="_blank" rel="noopener noreferrer" href="https://www.antgroup.com">
            😊 Profile
        </a>
        ),
        key: '0',
    },
    {
      label: (
        <>
          <a target="_blank" rel="noopener noreferrer" href="https://www.antgroup.com">
              🔆
          </a>
          <Switch onClick={(_, e) => e.stopPropagation()} checkedChildren="Light" unCheckedChildren="Dark" defaultChecked />
        </>
        ),
        key: '1',
    },
    {
        type: 'divider',
    },
    {
      label: (
        <a target="_blank" rel="noopener noreferrer" href="https://www.antgroup.com">
          ⚙️ Settings
        </a>
      ),
      key: '2',
    },
    {
      label: (
        <a target="_blank" rel="noopener noreferrer" href="https://www.aliyun.com">
          ⭕ Sign Out
        </a>
      ),
      key: '3',
    },
  ];

const UserInfo: React.FC = () => {
  const userName: string = useStore(state => state.userName);
  const userRole: string = useStore(state => state.userRole);
  if (userRole === '管理员') {
    return (
      <>
        🐲 {userName}
      </>
    )
  } else if (userRole === '普通用户') {
    return (
      <>
        🐱 {userName}
      </>
    )
  } else {
    return (
      <>
        ⚠️ {userName}
      </>
    )
  }
}

const UserMenu: React.FC = () => {
    return (
        <div className="lg:flex lg:flex-1 lg:justify-end text-sm font-semibold leading-6 text-gray-900">
            <Dropdown menu={{ items }} placement='bottomRight' trigger={['hover']}>
                <div onClick={(e) => e.preventDefault()}>
                    <UserInfo/>
                </div>
            </Dropdown>
        </div>
    )
}

export default UserMenu;
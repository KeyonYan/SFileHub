import { useStore } from '../../main';
import React from 'react';
import { DownOutlined } from '@ant-design/icons';
import type { MenuProps } from 'antd';
import { Button, Dropdown, Space } from 'antd';
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
        type: 'divider',
    },
    {
      label: (
        <a target="_blank" rel="noopener noreferrer" href="https://www.antgroup.com">
          ⚙️ Settings
        </a>
      ),
      key: '1',
    },
    {
      label: (
        <a target="_blank" rel="noopener noreferrer" href="https://www.aliyun.com">
          ⭕ Sign Out
        </a>
      ),
      key: '2',
    },
  ];

const UserMenu: React.FC = () => {
    const [userMenuOpen, setUserMenuOpen] = useState(false)
    const userName: string = useStore(state => state.userName);
    return (
        <div className="lg:flex lg:flex-1 lg:justify-end text-sm font-semibold leading-6 text-gray-900">
            <Dropdown menu={{ items }} placement='bottomRight'>
                <div>
                    {userName}
                </div>
            </Dropdown>
        </div>
    )
}

export default UserMenu;
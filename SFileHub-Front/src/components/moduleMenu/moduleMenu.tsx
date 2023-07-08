import { Popover } from '@headlessui/react'
import React from 'react';
import { NavLink } from 'react-router-dom';

const ModuleMenu: React.FC = () => {
    return (
        <Popover.Group className="hidden lg:flex lg:gap-x-12">
            <NavLink to="/home" className="text-sm font-semibold leading-6 text-gray-900">FileUpload</NavLink>
            <NavLink to="/home/download" className="text-sm font-semibold leading-6 text-gray-900">FileDownload</NavLink>
        </Popover.Group>
    )
}

export default ModuleMenu;
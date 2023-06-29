import React from 'react'
import UserMenu from '@/components/userMenu/UserMenu';
import ModuleMenu from '@/components/moduleMenu/ModuleMenu'
import IconView from '@/components/iconView/IconView'
import SearchView from '@/components/searchView/SearchView'

const NavBar: React.FC = () => {
  return (
    <header className="bg-white w-full">
      <nav className="mx-auto flex items-center justify-between p-6 lg:px-8" aria-label="Global">
        <div className="flex lg:flex-1 space-x-8">
          <IconView/>
          <SearchView/>
        </div>
        <ModuleMenu/>
        <UserMenu/>
      </nav>
    </header>
  )
}
export default NavBar;
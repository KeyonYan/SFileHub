import React from "react";
import { Input } from 'antd';

const { Search } = Input;

const onSearch = (value: string) => console.log(value);

const SearchView: React.FC = () => {
    return (
        <Search placeholder="Search datasets, algorithms..." onSearch={onSearch} style={{ width: 300 }} />
    )
}

export default SearchView;
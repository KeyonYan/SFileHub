import React from 'react'
import './errorPage.css'
// import { useRouteError } from "react-router-dom";

export default function ErrorPage() {
    // const error = useRouteError();
    // console.error(error);
    return (
        <div className="error-page grid place-content-center h-full">
          <div className='text-3xl'>Oops! That page couldn't be found </div>
        </div>
    );
}
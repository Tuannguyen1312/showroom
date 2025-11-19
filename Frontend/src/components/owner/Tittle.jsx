import React from 'react'

const Tittle = ({tittle, subTittle}) => {
  return (
    <>
    <h1 className='font-medium text-3xl'>{tittle}</h1>
    <p className='text-sm md:text-base text-gray-500/90 mt-2 max-w-156'>{subTittle}</p>
    </>
  )
}

export default Tittle
import React from 'react'

const Title = ({title, subTitle, aglin}) => {
  return (
    <div className={`flex flex-col justify-center items-center text-center ${aglin === "left" && "md:items-start md:start-left"}`}>
        <h1 className='font-semibold text-4xl md:text-[40px]'>{title}</h1>
        <p className='text-sm md:text-base  text-gray-500/90 mt-2  max-sw-156'>{subTitle}</p>
    </div>
  )
}

export default Title
import React, { useState } from 'react';
import "./DocEnreachment.css";

function DocEnreachment() {
  const [form, setForm]=useState({
    insureName:"AbC"
  })

  const handleChange=(e)=>{
    setForm({
      ...form,
      insureName:e.target.value
    })
  }
  return (
    <><div className='PrimaryTile'>
      <div className='file-wrapper'>
        <span>Insured Name</span>
        <div className="file-input-container">
          <input
            type="text"
            value={form.insureName}
            onChange={handleChange} />
        </div>
      </div>
      <div className='textMessage'>
        <span className='warningText'>Instruction:</span> <span> Provide company name to search in Dun & Bradstreet. Once a search has been completed, options will be presented. Select one of the options to move forward.</span>
      </div>
    </div>
   
     </>
  )
}

export default DocEnreachment
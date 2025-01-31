import React from 'react';

function Triag() {
  return (
   <>
   <div className='PrimaryTile'>

<div className='textMessageTriag'>
  <span className='warningText'>Instruction:</span>
  <span> Search with Entity number or company name in the Dun & Bradstreet database. Select suitable options to move forward.</span>
</div>

<div className="radio-group">
  <input
    className="form-check-input"
    type="radio"
    name="registered"
    id="registeredYes"
    value="true"
    checked
  />
  <label className="form-check-label "  htmlFor="registeredYes">
    Override the Screen
  </label>
</div>

</div>

   </>
  )
}

export default Triag
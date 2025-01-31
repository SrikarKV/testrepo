import { faAdd, faAngleLeft, faAngleRight, faChevronDown, faFilter, faSearch } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useState } from 'react'
import { Doughnut } from 'react-chartjs-2';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
ChartJS.register(ArcElement, Tooltip, Legend);
import './MainDashboard.css'
import { useNavigate } from 'react-router-dom';
import { Box, Button, Dialog, DialogActions, DialogContent, DialogTitle, Modal, TextField, Typography } from '@mui/material';


const MainDashboard = () => {
    const [reassignedTo, setReassignedTo] = useState('');
const navigate=useNavigate();
const [selectedCategoryCase,setSelectedCategoryCase]=useState('All-Cases');

const handleButtonClick=(category)=>{
    setSelectedCategoryCase(category)
}
const caseStatus = {
    labels: ["Open", "Resolved","In Progress", "Escalated", "Closed"],
    datasets: [
      {
        data: [35.0, 20.0, 25.0, 5.0,15.0],
        backgroundColor: ["#38812F", "#F4C145", "#519DE9", "#A20000","#FF701C"],
      },
    ],
  };

const chartOptions = {
    cutout: "80%", 
    maintainAspectRatio: false,
    responsive: true,
    plugins: {
      legend: false
    },
  };


 const submissionsData=[
    {
        title:'Total Submissions',
        count:60
    },
    {
        title:'Active Submissions',
        count:50
    },
    {
        title:'In Progress Submissions',
        count:20
    },

 ] 

 const tableData=[
    {
        caseId: 'CaseId-1',
        dateReceived:'29/01/25',
        dateDue:'29/01/25',
        insuredName:'John',
        AssignedTo:'Assigned To',
        status:'New Submission',
    },
    {
        caseId: 'CaseId-2',
        dateReceived:'29/01/25',
        dateDue:'29/01/25',
        insuredName:'Doe',
        AssignedTo:'Assigned To',
        status:'New Submission',
    },
    {
        caseId: 'CaseId-3',
        dateReceived:'29/01/25',
        dateDue:'29/01/25',
        insuredName:'Smith',
        AssignedTo:'Assigned To',
        status:'New Submission',
    },
    {
        caseId: 'CaseId-4',
        dateReceived:'29/01/25',
        dateDue:'29/01/25',
        insuredName:'Lily',
        AssignedTo:'Assigned To',
        status:'New Submission',
    },
    {
        caseId: 'CaseId-5',
        dateReceived:'29/01/25',
        dateDue:'29/01/25',
        insuredName:'Joy',
        AssignedTo:'Assigned To',
        status:'New Submission',
    },
    {
        caseId: 'CaseId-6',
        dateReceived:'29/01/25',
        dateDue:'29/01/25',
        insuredName:'John',
        AssignedTo:'Assigned To',
        status:'New Submission',
    },
    {
        caseId: 'CaseId-6',
        dateReceived:'29/01/25',
        dateDue:'29/01/25',
        insuredName:'Doe',
        AssignedTo:'Assigned To',
        status:'New Submission',
    },
    {
        caseId: 'CaseId-7',
        dateReceived:'29/01/25',
        dateDue:'29/01/25',
        insuredName:'Lily',
        AssignedTo:'Assigned To',
        status:'New Submission',
    },
    {
        caseId: 'CaseId-8',
        dateReceived:'29/01/25',
        dateDue:'29/01/25',
        insuredName:'Smith',
        AssignedTo:'Assigned To',
        status:'New Submission',
    }
 ]
   const [currentPage, setCurrentPage] = useState(1);
 const totalCount=tableData.length
 const itemsPerPage = 5;
 const totalPages = Math.ceil(tableData.length / itemsPerPage);

 const paginate = (array, pageNumber, itemsPerPage) =>
  array.slice((pageNumber - 1) * itemsPerPage, pageNumber * itemsPerPage);

const currentData = paginate(tableData, currentPage, itemsPerPage);

const handlePageClick = (pageNumber) => {
  setCurrentPage(pageNumber);
};

const [showPopup, setShowPopup] = useState(false);
  const handleShow = () => setShowPopup(true);

  const handleClose = () => setShowPopup(false);

  const handleSaveReassignment = () => {
    console.log(`Reassigned to: ${reassignedTo}`);
    navigate('/dashboard/uploadfile');  
    setShowPopup(false);  
  };

return (
<div className='LayoutSection'>
<div className="case-card">
<div className='case-chart-data'>
    <div className='chart-list'>
        <h3>Welcome to the Submission Hub Dashboard</h3>
        <h2 className='user-title'>John Doe</h2>
    </div>
    <div className='chart-list-content'>
                            <button className="back-btn" onClick={()=>{navigate('/')}}>
                                Back to Landing page
                            </button>
                            <button className="underwriter-btn">
                                Underwriter <FontAwesomeIcon icon={faChevronDown} />
                            </button>
    </div>
</div>
</div>
    <div className='case-chart'>
       <div className="bg-chart">
   <div className='case-chart-data'>
   <h3 className=''>Cases Status</h3>
   <p className=''>As of: 29/01/2025, 16:07</p>
   </div>
 
 
  <div className="case-chart-size ">
    <div className='case-chart-data'>
    <div className='chart-size-circle'>
<div className='chart-size'>
<Doughnut data={caseStatus} options={chartOptions} />
</div>
</div>
</div>

    <div className="data-chart-values case-chart-data">
      {caseStatus.labels.map((label, index) => (
        <div key={index} className="chart-list">
            <div className='chart-list-content'>
          <span
            style={{
              width: "12px",
              height: "12px",
              backgroundColor: caseStatus.datasets[0].backgroundColor[index],
            }}
          ></span>
          <span>{label}</span>
          </div>
          <span className="per-ms">{caseStatus.datasets[0].data[index]}.0 %</span>
        </div>
      ))}
    </div>
  </div>
</div>


<div className='kpi-list'>
{submissionsData.map((item,index)=>(
<div key={index} className='kpi-list-content'>
      <p>{item.title}</p>
      <h6 className='kpi-count'>{item.count}</h6>
    </div>
))}
</div>

</div>
<hr className='line-case'/>

<div className='bg-cases-subtab'>
   <div className='subtab-list-content'>
   <button
        onClick={() => handleButtonClick("All-Cases")}
        className={`btn ${selectedCategoryCase === "All-Cases" ? "active-btn-case" : "btn-sec-case"}`}
      >
        All Cases
      </button>
      <button
        onClick={() => handleButtonClick("My-Cases")}
        className={`btn ${selectedCategoryCase=== "My-Cases" ? "active-btn-case" : "btn-sec-case"}`}
      >
        My Cases
      </button>
    
   </div>
   <div className='chart-list-content'>
    <div style={{position:'relative'}}>
   <input 
type="search" 
placeholder="Search..." 
className='search-case'
/>
<FontAwesomeIcon icon={faSearch} className='search-case-icon'/>
</div>
<button className='btn-case-filter'>
<FontAwesomeIcon icon={faFilter} />
</button>
<button className='btn-case-filter-btn'>
Clear All
</button>
<button className='btn-case-new' onClick={()=>{navigate('/dashboard/uploadfile')}}>
<FontAwesomeIcon icon={faAdd} /> New Submission
</button>
   </div>
</div>
<div className='my-4'>
<table className='case-table table-bordered'>
    <thead style={{background:" #E4E7EC"}}>
    <tr className='text-nowrap'>
        <th>Case ID</th>
        <th>Date Received</th>
        <th>Date Until Due</th>
        <th>Insured Name</th>
        <th>Assigned To</th>
        <th>Status</th>
        <th>Action</th>
        </tr>
    </thead>
    <tbody>
    {currentData.length > 0 ? (
            currentData.map((caseData) => (
        <tr key={caseData.caseId}>
            <td><button className='case-action' onClick={()=>{navigate('/dashboard/uploadfile')}}>{caseData.caseId}</button></td>
            <td>{caseData.dateReceived}</td>
            <td>{caseData.dateDue}</td>
            <td>{caseData.insuredName}</td>
            <td >{caseData.AssignedTo}</td>
            <td>
                <span className='case-status'>{caseData.status}</span></td>
            <td>
                <button className='case-action' onClick={handleShow}>Reassign</button>
            </td>
        </tr>
            )) 
        ) : (
            
            
                <tr>
                    <td colSpan={8} className='text-center'> No cases found</td>
                </tr>
        )}
            
    </tbody>
</table>
<div className='page-data'>
<div className="page-list">
              
               <div className="page-list-content">
                 <button
                   variant=""
                   className={currentPage  === 1? 'primary-case' : 'primary-active'}
                   disabled={currentPage === 1}
                   onClick={() => handlePageClick(currentPage - 1)}
                 >
                   <FontAwesomeIcon icon={faAngleLeft}/>
                 </button>
                 {/* {[...Array(totalPages)].map((_, index) => (
                   <button
                     key={index}
                     className={index + 1 === currentPage ? 'primary-active-page' : 'primary-case-page'}
                     onClick={() => handlePageClick(index + 1)}
                   >
                     {index+1} 
                   </button>
                 ))} */}
                 <div className="case-page">

<button className='primary-active-page'>{currentPage}</button> / {Math.min(currentPage * itemsPerPage, totalCount)}
</div>
                 <button
                   className={currentPage === totalPages ? 'primary-case' : 'primary-active'}
                   disabled={currentPage === totalPages}
                   onClick={() => handlePageClick(currentPage + 1)}
                 >
                   <FontAwesomeIcon icon={faAngleRight}/>
                 </button>
               </div>
               <div>
               <span>
               {/* {(currentPage - 1) * itemsPerPage + 1} */}
                 Showing {' '}
                 {Math.min(currentPage * itemsPerPage, totalCount)} of {totalCount} records
               </span>
             </div>
             </div>
  </div>
  
 
</div>
<Dialog open={showPopup} onClose={handleClose}>
        <DialogTitle style={{ backgroundColor: '#132f57', color: '#fff' }}>Reassign Submissions</DialogTitle>
        <DialogContent>
          <p style={{marginTop:'20px'}}>Are you sure you want to reassign the submission?</p>
          <div style={{marginTop:'20px'}}>
            <TextField
              id="reassign-to"
              label="Reassigned to"
              variant='outlined'
              type="text"
              value={reassignedTo}
              onChange={(e) => setReassignedTo(e.target.value)}
              placeholder="Enter name or ID"
              fullWidth
              sx={{padding:'0px'}}
            />
          </div>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} className='button-save'>
            Close
          </Button>
          <Button onClick={handleSaveReassignment} className='button-close'>
            Save
          </Button>
        </DialogActions>
      </Dialog>
</div>
)
}
   

export default MainDashboard

import React, { useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import LoaderGif from '../../assets/LoaderGif.gif';
import "./UploadFile.css";

function UploadFile() {
    const navigate = useNavigate();
    const fileInputRef = useRef(null);
    const [fileName, setFileName] = useState("");
    const [selectedOption, setSelectedOption] = useState("device");
    const [showModal, setShowModal] = useState(false);

    const handleRadioChange = (event) => {
        setSelectedOption(event.target.value);
        setFileName(""); 
    };

    const handleClick = () => {
        fileInputRef.current.click();
    };

    const handleFileChange = (event) => {
        if (event.target.files.length > 0) {
            setFileName(event.target.files[0].name);
        } else {
            setFileName("");
        }
    };

    const handleCancel = () => {
        setFileName(""); 
    };

    const handleUpload = () => {
        setShowModal(true);
        setTimeout(() => {
            setShowModal(false);
            navigate('/dashboard/submission-bench');
        }, 3000);
    };

    return (
        <div className='Layout'>
            <div className='LabelTab'>Document Review</div>
            <div className='PrimaryTile'>
                <div className='labelText'>Select how you want to upload the file?</div>
                <div style={{ display: 'flex', gap: '1.5rem' }}>
                    <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', gap: '0.5rem' }}>
                        <input
                            type='radio'
                            name="uploadOption"
                            value="device"
                            checked={selectedOption === "device"}
                            onChange={handleRadioChange}
                        />
                        <p style={{ margin: '0' }}>Upload from device</p>
                    </div>
                    <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', gap: '0.5rem' }}>
                        <input
                            type='radio'
                            name="uploadOption"
                            value="existing"
                            checked={selectedOption === "existing"}
                            onChange={handleRadioChange}
                        />
                        <p style={{ margin: '0' }}>Upload from Existing file</p>
                    </div>
                </div>
            </div>
            {selectedOption === "device" && (
                <div className='PrimaryTile'>
                    <div className='labelText'>Upload Document to Manually Create a New Submission</div>
                    <div className='XsmallLabel'>
                        <span className='warningText'>Instruction: </span> Upload a PDF to start the process
                    </div>
                    <div className='file-wrapper'>
                        <span>File Upload</span>
                        <div className="file-upload-wrapper" onClick={handleClick}>
                            <input
                                type="file"
                                ref={fileInputRef}
                                className="file-upload-input"
                                accept="application/pdf"
                                onChange={handleFileChange}
                            />
                            <div className="custom-file-upload">
                                {fileName ? fileName : "Drag and Drop your files here or Browse your files"}
                            </div>
                        </div>

                        <div className='btn-section'>
                            <button className="upload-btn" onClick={handleUpload}>Upload</button>
                            <button className="cancel-btn" onClick={handleCancel}>Cancel</button>
                        </div>
                    </div>
                </div>
            )}
            {selectedOption === "existing" && (
                <div className='PrimaryTile'>
                    <div className='file-wrapper'>
                        <span>Select Document</span>
                        <div className="file-input-container">
                            <input type="text" value={fileName} placeholder="No file chosen" readOnly onClick={() => document.getElementById('fileUpload').click()} />
                            <input type="file" id="fileUpload" onChange={handleFileChange} hidden />
                            <button className="upload-btn" onClick={handleUpload}>Upload</button>
                            <button className="cancel-btn" onClick={handleCancel}>Cancel</button>
                        </div>
                    </div>
                </div>
            )}
            
            {showModal && (
                <>
                    <div className="modal-backdrop"></div>
                    <div className="modal-overlay">
                        <div className="modal-content">
                            <div className="loader">
                                <img src={LoaderGif} alt="Loading..." />
                            </div>
                            <h3>Creating Case... </h3>
                            <p>Please wait</p>
                        </div>
                    </div>
                </>
            )}
        </div>
    );
}

export default UploadFile;

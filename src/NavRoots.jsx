import { createBrowserRouter } from "react-router-dom";
import App from "./App";
import MainDashboard from "./components/Pages/MainDashboard/MainDashboard";
import UploadFile from "./components/Pages/UploadFile";
import SubmissionWorkbench from "./components/SubmissionWorkBench/SubmissionWorkbench";
import Landingpage from "./components/MainNav/Landingpage/Landingpage";

export const NavRoots=createBrowserRouter([
    {
        path:'/',
        element:<Landingpage/>
    },
    {
        path:'/dashboard',
        element:<App/>,
        children:[
            {
                index:true,
                element: <MainDashboard/>
            },
            {
                path:'uploadfile',
                element: <UploadFile/>
            },
            {
                path:'submission-bench',
                element: <SubmissionWorkbench/>
            }
        ]
    }
])
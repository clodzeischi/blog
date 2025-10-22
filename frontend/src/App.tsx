import {BrowserRouter, Route, Routes} from "react-router-dom";
import {PostList} from "./pages/PostList.tsx";
import {PostDetails} from "./pages/PostDetails.tsx";

function App() {

  return (
    <>
        <BrowserRouter>
            <Routes>
                <Route path='/' element={<PostList />} />
                <Route path='posts/:id' element={<PostDetails />} />
            </Routes>
        </BrowserRouter>
    </>
  )
}

export default App

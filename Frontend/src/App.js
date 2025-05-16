import { BrowserRouter as Router,Routes,Route } from "react-router-dom";
import MyForm from "./MyForm";

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<MyForm />} />
        {/* Add more routes here if needed */}
      </Routes>
    </Router>  )
}

export default App
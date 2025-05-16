import { BrowserRouter as Router,Routes,Route } from "react-router-dom";
import UserForm from "./UserForm";
const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<UserForm />} />
        {/* Add more routes here if needed */}
      </Routes>
    </Router>  )
}

export default App
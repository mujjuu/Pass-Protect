import { BrowserRouter as Router,Routes,Route } from "react-router-dom";
import UserForm from "./UserForm";
import Home from "./Home";
import Login from "./Login";
import Details from "./Details";
const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<UserForm />} />
        <Route path="/home" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/details" element={<Details />} />

        {/* Add more routes here if needed */}
      </Routes>
    </Router>  )
}

export default App;
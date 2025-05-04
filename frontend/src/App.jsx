import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Layout from './components/Layout/Layout.jsx';
import Polls from './components/Polls/Polls.jsx';
import CreatePoll from './components/CreatePoll/CreatePoll.jsx';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Polls />} />
          <Route path="create" element={<CreatePoll />} />
        </Route>
      </Routes>
    </Router>
  )
}

export default App

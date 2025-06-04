import React, { useEffect, useState } from 'react';
import axios from 'axios';

function App() {
  const [users, setUsers] = useState([]);
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    addressLine1: '',
    addressLine2: '',
    city: '',
    state: '',
    country: ''
  });
  const [editingEmail, setEditingEmail] = useState(null);

  const fetchUsers = async () => {
    const res = await axios.get('http://localhost:8080/api/users');
    setUsers(res.data);
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (editingEmail) {
      await axios.put(`http://localhost:8080/api/users/${editingEmail}`, formData);
    } else {
      await axios.post('http://localhost:8080/api/users', formData);
    }
    setFormData({
      firstName: '', lastName: '', email: '', addressLine1: '',
      addressLine2: '', city: '', state: '', country: ''
    });
    setEditingEmail(null);
    fetchUsers();
  };

  const handleEdit = (user) => {
    setFormData(user);
    setEditingEmail(user.email);
  };

  const handleDelete = async (email) => {
    await axios.delete(`http://localhost:8080/api/users/${email}`);
    fetchUsers();
  };

  return (
    <div className="p-6 max-w-6xl mx-auto">
      <h1 className="text-2xl font-bold mb-4">User Registration Form</h1>
      <form className="grid grid-cols-2 gap-4" onSubmit={handleSubmit}>
        {Object.entries(formData).map(([key, val]) => (
          <input
            key={key}
            className="border p-2 rounded"
            placeholder={key}
            name={key}
            value={val}
            onChange={handleChange}
            required={key !== 'addressLine2'}
          />
        ))}
        <button type="submit" className="col-span-2 bg-blue-600 text-white py-2 rounded">
          {editingEmail ? 'Update User' : 'Add User'}
        </button>
      </form>

      <h2 className="text-xl font-semibold mt-8 mb-4">User List</h2>
      <table className="w-full border">
        <thead>
          <tr className="bg-gray-200">
            <th className="border px-2">First</th>
            <th className="border px-2">Last</th>
            <th className="border px-2">Email</th>
            <th className="border px-2">City</th>
            <th className="border px-2">State</th>
            <th className="border px-2">Country</th>
            <th className="border px-2">Actions</th>
          </tr>
        </thead>
        <tbody>
          {users.map((user,idx) => {
            if(idx<=1)
              return <></>;

            return(
            <tr key={user.email}>
              <td className="border px-2">{user.firstName}</td>
              <td className="border px-2">{user.lastName}</td>
              <td className="border px-2">{user.email}</td>
              <td className="border px-2">{user.city}</td>
              <td className="border px-2">{user.state}</td>
              <td className="border px-2">{user.country}</td>
              <td className="border px-2 space-x-2">
                <button onClick={() => handleEdit(user)} className="bg-yellow-500 text-white px-2 py-1 rounded">Edit</button>
                <button onClick={() => handleDelete(user.email)} className="bg-red-600 text-white px-2 py-1 rounded">Delete</button>
              </td>
            </tr>
          )})}
        </tbody>
      </table>
    </div>
  );
}

export default App;


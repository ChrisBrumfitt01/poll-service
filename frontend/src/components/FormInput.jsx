import './FormInput.css';

export default function FormInput({ label, name, type }) {
    return (
      <div className="form-input-container">
        <label className='form-label'>{label}</label>
        <input className='input' type={type} name={name} required />
      </div>
    );
}
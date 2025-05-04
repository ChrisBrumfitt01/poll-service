export default function FormInput({ label, name, type }) {
    return (
      <div className="form-input-container">
        <label>{label}</label>
        <input type={type} name={name} required />
      </div>
    );
}
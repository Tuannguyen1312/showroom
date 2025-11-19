import React, { useState } from 'react'

const Register = ({ setShowRegister, setShowLogin}) => {
  const [formData, setFormData] = useState({
    fullName: '',
    phone: '',
    address: '',
    email: '',
    password: '',
    confirmPassword: '',
  });

  const [otp, setOtp] = useState('');
  const [generatedOtp, setGeneratedOtp] = useState('');
  const [step, setStep] = useState(1);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
    setError('');
    setSuccess('');
  };

  const generateOtp = () => {
    const code = Math.floor(100000 + Math.random() * 900000).toString();
    setGeneratedOtp(code);
    console.log('OTP Sent:', code); // 🧠 Giả lập gửi OTP
    setSuccess(`OTP sent to ${formData.email || formData.phone}`);
    setStep(2);
  };

  const handleVerifyOtp = (e) => {
    e.preventDefault();
    if (otp === generatedOtp) {
      setSuccess('✅ Registration successful (frontend only)');
      setError('');
    } else {
      setError('❌ Invalid OTP. Please try again.');
    }
  };

  const handleRegister = (e) => {
    e.preventDefault();
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const phoneRegex = /^[0-9]{9,11}$/;

    if (!formData.fullName || !formData.phone || !formData.address || !formData.email || !formData.password)
      return setError('Please fill in all fields.');
    if (!emailRegex.test(formData.email)) return setError('Invalid email format');
    if (!phoneRegex.test(formData.phone)) return setError('Invalid phone number');
    if (formData.password !== formData.confirmPassword)
      return setError('Passwords do not match');

    generateOtp();
  };

  return (
    <div onClick={() => setShowRegister(false)} className="fixed inset-0 bg-black/50 z-50 flex items-center justify-center text-sm">
      <form onClick={(e) => e.stopPropagation()} className="bg-white rounded-2xl p-8 w-96 shadow-xl transition-all">

        <h2 className="text-2xl font-semibold text-center text-gray-800 mb-6">
          {step === 1 ? 'Create an Account' : 'Verify OTP'}
        </h2>

        {error && <p className="text-red-500 text-center mb-3">{error}</p>}
        {success && <p className="text-green-600 text-center mb-3">{success}</p>}

        {step === 1 ? (
          <>
            <input name="fullName" placeholder="Full Name" value={formData.fullName} onChange={handleChange}
              className="w-full border border-gray-300 rounded-lg px-3 py-2 mb-3 outline-none" required />

            <input name="phone" placeholder="Phone Number" value={formData.phone} onChange={handleChange}
              className="w-full border border-gray-300 rounded-lg px-3 py-2 mb-3 outline-none" required />
              
              <input name="ID" placeholder="Your ID" value={formData.ID} onChange={handleChange}
              className="w-full border border-gray-300 rounded-lg px-3 py-2 mb-3 outline-none" required />

            <input name="address" placeholder="Address" value={formData.address} onChange={handleChange}
              className="w-full border border-gray-300 rounded-lg px-3 py-2 mb-3 outline-none" required />

            <input name="email" type="email" placeholder="Email Address" value={formData.email} onChange={handleChange}
              className="w-full border border-gray-300 rounded-lg px-3 py-2 mb-3 outline-none" required />

            <input name="password" type="password" placeholder="Password" value={formData.password} onChange={handleChange}
              className="w-full border border-gray-300 rounded-lg px-3 py-2 mb-3 outline-none" required />

            <input name="confirmPassword" type="password" placeholder="Confirm Password"
              value={formData.confirmPassword} onChange={handleChange}
              className="w-full border border-gray-300 rounded-lg px-3 py-2 mb-4 outline-none" required />

            <button onClick={handleRegister}
              className="w-full bg-indigo-600 hover:bg-indigo-700 text-white py-2 rounded-lg transition-all">
              Send OTP
            </button>
          </>
        ) : (
          <>
            <input type="text" placeholder="Enter 6-digit OTP" value={otp}
              onChange={(e) => setOtp(e.target.value)} maxLength="6"
              className="w-full border border-gray-300 rounded-lg px-3 py-2 mb-3 outline-none text-center tracking-widest" />

            <button onClick={handleVerifyOtp}
              className="w-full bg-green-600 hover:bg-green-700 text-white py-2 rounded-lg transition-all">
              Verify OTP
            </button>
          </>
        )}

        <p className="text-center text-gray-500 mt-4">
          Already have an account?{' '}
          <span className="text-indigo-600 hover:underline cursor-pointer"
            onClick={() => { setShowRegister(false); setShowLogin(true); }}>
            Login
          </span>
        </p>
      </form>
    </div>
  )
}

export default Register

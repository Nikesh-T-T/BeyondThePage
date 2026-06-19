import React from 'react';
import { NavLink, useNavigate } from 'react-router-dom';

const navItems = [
  { to: '/', icon: 'dashboard', label: 'Dashboard', exact: true },
  { to: '/books', icon: 'menu_book', label: 'Books' },
  { to: '/weekly', icon: 'view_week', label: 'Weekly View' },
  { to: '/daily', icon: 'view_day', label: 'Daily View' },
];

const Sidebar: React.FC = () => {
  const navigate = useNavigate();

  return (
    <aside className="fixed left-0 top-0 h-screen w-[280px] bg-surface-container-low border-r border-outline-variant flex flex-col p-md gap-sm shadow-sm z-50">
      <div className="flex items-center gap-3 px-2 py-4 mb-2">
        <div className="w-9 h-9 rounded-lg bg-primary flex items-center justify-center text-on-primary">
          <span className="material-symbols-outlined text-[20px]">menu_book</span>
        </div>
        <h1 className="text-[17px] font-bold text-primary leading-tight">Beyond The Page</h1>
      </div>

      <nav className="flex-grow space-y-1">
        {navItems.map(item => (
          <NavLink
            key={item.to}
            to={item.to}
            end={item.exact}
            className={({ isActive }) =>
              `flex items-center gap-md p-md rounded-lg transition-all duration-200 text-body-sm font-medium ` +
              (isActive
                ? 'bg-primary text-on-primary font-bold'
                : 'text-on-surface-variant hover:bg-surface-container-high')
            }
          >
            <span className="material-symbols-outlined text-[20px]">{item.icon}</span>
            <span>{item.label}</span>
          </NavLink>
        ))}
      </nav>

      <button
        onClick={() => navigate('/books/new')}
        className="w-full py-md bg-primary text-on-primary font-bold rounded-lg hover:opacity-90 active:scale-95 transition-all flex items-center justify-center gap-2"
      >
        <span className="material-symbols-outlined text-[20px]">add</span>
        Add New Book
      </button>
    </aside>
  );
};

export default Sidebar;

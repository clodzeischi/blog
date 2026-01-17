import {Outlet} from "react-router";
import {Toaster} from "sonner";

export const RootLayout = () => {
    return (
        <div className="min-h-screen flex flex-col">
            <Navbar />
            <Toaster richColors />
            <main className="flex-1 container mx-auto py-6">
                <Outlet />
            </main>
        </div>
    )
}
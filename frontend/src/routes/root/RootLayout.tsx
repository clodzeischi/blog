import {Outlet} from "react-router"
import {Toaster} from "sonner"
import {Navbar} from "@/components/Navbar.tsx";

export const RootLayout = () => {
    return (
        <div className="min-h-screen flex flex-col">
            <Navbar className={"w-full h-15 border"}/>
            <Toaster richColors />
            <main className="flex-1 container mx-auto py-6">
                <Outlet />
            </main>
        </div>
    )
}